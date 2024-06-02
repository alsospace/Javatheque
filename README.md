# Javatheque

Javatheque is a project to create an API rest for managing a video library using Java Jakarta

This project is part of module RT0805 of the Master DAS.
## Authors

- Dupont Corentin
- Morlet Flavien

## Usage

Before you start, you'll need to install **Glassfish**.

### Start a domain

```powershell
asadmin start-domain [domaine-name]
```

### Deploy for the first time

```powershell
.\mvnw clean package

asadmin deploy .\target\javatheque.war
```

### Redeploy

```powershell
.\mvnw clean package

asadmin redeploy --name javatheque .\target\javatheque.war
```

