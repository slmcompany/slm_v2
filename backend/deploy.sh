#!/bin/bash

set -e

echo "=========================================="
echo "🚀 SLM API V2 Deployment Script"
echo "=========================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration
PROJECT_NAME="slm-api-v2"
JAR_FILE="target/*.jar"

# Function to print colored messages
print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Docker is running
print_info "Checking Docker..."
if ! docker info > /dev/null 2>&1; then
    print_error "Docker is not running. Please start Docker first."
    exit 1
fi

# Check if docker-compose is available
if ! command -v docker-compose &> /dev/null; then
    print_error "docker-compose is not installed."
    exit 1
fi

print_info "Docker and docker-compose are ready."

# Give execute permission to mvnw if needed
if [ ! -x "./mvnw" ]; then
    print_info "Setting execute permission for mvnw..."
    chmod +x ./mvnw
fi

# Step 1: Clean old builds
print_info "Cleaning old builds..."
./mvnw clean

# Step 2: Build the application
print_info "Building application with Maven..."
./mvnw package -DskipTests

# Check if JAR file was created
if ! ls $JAR_FILE 1> /dev/null 2>&1; then
    print_error "JAR file not found in target directory. Build failed!"
    exit 1
fi

print_info "Build successful! JAR file created."

# Step 3: Stop and remove old containers
print_info "Stopping existing containers..."
docker-compose down --remove-orphans

# Step 4: Remove old images (optional)
print_warn "Removing old Docker images..."
docker rmi ${PROJECT_NAME}:latest 2>/dev/null || true
docker image prune -f

# Step 5: Create necessary directories
print_info "Creating necessary directories..."
mkdir -p logs/nginx

# Step 6: Build and start containers
print_info "Building and starting Docker containers..."
docker-compose up -d --build

# Step 7: Wait for services to be healthy
print_info "Waiting for services to be healthy..."
sleep 10

# Check container status
print_info "Checking container status..."
docker-compose ps

# Step 8: Show logs
print_info "Showing container logs (last 50 lines)..."
docker-compose logs --tail=50

# Step 9: Health check
print_info "Performing health check..."
sleep 5

if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    print_info "✅ Spring Boot API is healthy!"
else
    print_warn "⚠️  Spring Boot API health check failed. Check logs with: docker-compose logs -f slm-api-v2"
fi

if curl -f http://localhost/actuator/health > /dev/null 2>&1; then
    print_info "✅ Nginx proxy is healthy!"
else
    print_warn "⚠️  Nginx health check failed. Check logs with: docker-compose logs -f nginx"
fi

echo ""
echo "=========================================="
print_info "🎉 Deployment completed!"
echo "=========================================="
echo ""
echo "📌 Useful commands:"
echo "  - View logs:        docker-compose logs -f"
echo "  - View API logs:    docker-compose logs -f slm-api-v2"
echo "  - View Nginx logs:  docker-compose logs -f nginx"
echo "  - Stop containers:  docker-compose down"
echo "  - Restart:          docker-compose restart"
echo ""
echo "🌐 Access points:"
echo "  - API (direct):     http://localhost:8080"
echo "  - API (via nginx):  http://localhost"
echo "  - Health check:     http://localhost/actuator/health"
echo "  - Swagger UI:       http://localhost/swagger-ui.html"
echo ""