
# NOTICE
!!Do not use this project in any sort of production, this is merely a practice project!!

# Requirements
1. MSSQL locally installed for local development
2. Docker desktop (at least version 4.30.0 (149282), since it is the one I used)
3. Some room for MSSQL image
   - At the time of this documentation the testcontainers MSSQL installation size was 1.58GB (MSSQL-container) + 15.22MB (some testcontainer -container) = 1.595GB
   - Not sure about RAM recommendation, but at least 8GB _should_ be sufficient
4. Azurite
   - In development Azurite is recommended due to: 
     - Free development (local resources do not cost)
     - Easier integration testing (fast startup times, and no reliance on internet + azure)
     - Faster startup times (azure resources do not need to exist, in order to start development)
     - Simple dev resource cleanup (doesn't really matter if something is deleted, or still exists)

# Configuration steps
1. Install  MSSQL locally
2. Open up port 1433 for MSSQL, and enable TCP/IP
3. Create the src/main/resources/application.properties -file (there is an example for settings)

# Usefull commands

## Start azurite
````
azurite -s -l j:\azurite -d j:\azurite\debug.log
````

# Documentation
- https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/storage/azure-storage-blob/README.md

