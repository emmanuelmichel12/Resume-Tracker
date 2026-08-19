CREATE SCHEMA authService;
CREATE SCHEMA trackingService;
CREATE SCHEMA aiService;
CREATE SCHEMA notificationService;

CREATE TABLE authService.users(
	id BIGSERIAL PRIMARY KEY,
	firstname VARCHAR(100),
	lastname VARCHAR(100),
	email VARCHAR(300) UNIQUE NOT NULL,
	username VARCHAR(100) UNIQUE NOT NULL,
	password VARCHAR(250) NOT NULL,
	createdAt TIMESTAMP DEFAULT NOW()
);

CREATE TABLE trackingService.applications(
	id BIGSERIAL PRIMARY KEY,
	userId BIGINT NOT NULL,
	jobName VARCHAR(200),
	jobRole VARCHAR(200),
	jobUrl VARCHAR(500),
	jobStatus VARCHAR(100),
	dateApplied DATE,
	notes TEXT,
	createdAt TIMESTAMP DEFAULT NOW(),
	updatedAt TIMESTAMP DEFAULT NOW()
);

CREATE TABLE aiService.resumes(
	id BIGSERIAL PRIMARY KEY,
	userId BIGINT NOT NULL,
	applicationId BIGINT,
	s3Url VARCHAR(500),
	resumeText TEXT,
	jobDescription TEXT,
	aiOputput TEXT,
	createdAt TIMESTAMP DEFAULT NOW()
);

CREATE TABLE notificationService.notifications(
	id BIGSERIAL PRIMARY KEY,
	userId BIGINT NOT NULL,
	applicationId BIGINT,
	notificationType VARCHAR(100),
	message TEXT,
	wasSent BOOLEAN DEFAULT FALSE,
	scheduledFor TIMESTAMP,
	sentAt TIMESTAMP,
	createdAt TIMESTAMP DEFAULT NOW()
);

SELECT table_schema, table_name 
FROM information_schema.tables 
WHERE table_name = 'users';

SELECT schema_name FROM information_schema.schemata;
ALTER TABLE aiservice.resumes RENAME COLUMN aioputput TO aiOutput;
