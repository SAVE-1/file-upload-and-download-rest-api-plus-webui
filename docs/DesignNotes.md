# Design notes

## Date times
- Client date times should not contain nanoseconds for clarity
- Database date times should contain nanoseconds for analysis purposes

## API endpoints

### File upload and download

#### POST /api/file/

#### GET /api/file/{filename:.+}

#### DELETE /api/file/{filename:.+}

#### GET /api/file/myfiles

#### GET /api/file/info/{filename}

### Registration and logging in

#### /auth/signup

#### /auth/login

### File sharing

#### GET /api/file/share/ ???







