# Feedback ATA Microservices (Clean Architecture)

## Project Timeline

### User Service Development (October - January)
- **Week 4-5 (October)**
  - [x] USR-01: Login Page Implementation
  - [x] USR-02: Login Validation
  - [x] USR-03: Login Response Dialog
  - [ ] USR-04: Forget Password Dialog

- **Week 1-2 (December)**
  - [ ] USR-05: Forget Password Validation
  - [ ] USR-06: Forget Password Response Dialog
  - [ ] USR-07: Setup JWT Authentication
  - [ ] USR-08: Implement RBAC

### Feedback Service Development (December - February)
- **Week 1-2 (December)**
  - [ ] FDB-01: Project Structure Setup
  - [ ] FDB-02: Database Schema Design
  - [ ] FDB-03: Flyway Migration Setup
  - [ ] FDB-04: Entity Models Implementation

- **Week 3-4 (December)**
  - [ ] FDB-05: Repository Layer
  - [ ] FDB-06: Service Layer
  - [ ] FDB-07: Controller Layer
  - [ ] FDB-08: JWT Integration

- **Week 1-2 (January)**
  - [ ] FDB-09: Question Management API
  - [ ] FDB-10: Feedback Submission API
  - [ ] FDB-11: Analytics API
  - [ ] FDB-12: Report Generation

### Infrastructure & DevOps (January - February)
- **Week 3-4 (January)**
  - [ ] DEV-01: Docker Compose Setup
  - [ ] DEV-02: Prometheus Integration
  - [ ] DEV-03: Grafana Dashboard
  - [ ] DEV-04: ELK Stack Integration

- **Week 1-2 (February)**
  - [ ] DEV-05: CI/CD Pipeline
  - [ ] DEV-06: Kubernetes Deployment
  - [ ] DEV-07: Service Mesh (Istio)
  - [ ] DEV-08: API Gateway

## Infrastructure Components

### Monitoring & Observability
- **Prometheus**
  - Metrics Collection
  - Alert Rules
  - Service Health Monitoring

- **Grafana**
  - Custom Dashboards
  - Performance Metrics
  - Resource Utilization

### Logging & Tracing
- **ELK Stack**
  - Elasticsearch
  - Logstash
  - Kibana
  - APM (Application Performance Monitoring)

### Database Migrations
- **Flyway**
  - Version Control for DB Schema
  - Migration Scripts
  - Baseline Management

### Service Discovery
- **Eureka Server**
  - Service Registration
  - Load Balancing
  - Health Checks

### Security
- **JWT Authentication**
  - Token Management
  - Role-Based Access
  - Security Filters

## Task Dependencies
### User Service
- USR-02 depends on USR-01
- USR-03 depends on USR-02
- USR-05 depends on USR-04
- USR-06 depends on USR-05

### Feedback Service
- FDB-02 depends on FDB-01
- FDB-03 depends on FDB-02
- FDB-05 depends on FDB-04
- FDB-08 depends on USR-07

### DevOps
- DEV-02 depends on DEV-01
- DEV-03 depends on DEV-02
- DEV-06 depends on DEV-05
- DEV-08 depends on DEV-07

## Progress Legend
- [x] Completed
- [ ] In Progress
- [ ] Not Started

## Project Owner
- Lead Developer: Bengi

## Documentation
- API Documentation: Swagger UI
- Architecture: Clean Architecture
- Infrastructure: Docker & Kubernetes
- Monitoring: Prometheus & Grafana
