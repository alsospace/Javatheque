### Start a domain

asadmin start-domain [domaine-name]

### Deploy for the first time

```powershell
.\mvnw clean package cargo:run

asadmin deploy .\target\javatheque.war
```

### Redeploy

```powershell
.\mvnw clean package cargo:run

asadmin redeploy --name javatheque .\target\javatheque.war
```

