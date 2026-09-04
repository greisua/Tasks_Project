CREATE TABLE users (
    id BIGINT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	surname1 VARCHAR(255)NOT NULL,
	surname2 VARCHAR(255),
	email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE projects (
    id BIGINT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255),
	creationDate DATE NOT NULL,
	
	UNIQUE (name,creationDate)
);

CREATE TABLE users_projects (
    user_id      BIGINT NOT NULL,
	project_id   BIGINT NOT NULL,
	leader       BOOLEAN NOT NULL,
	
	PRIMARY KEY (user_id, project_id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE tasks (
    id BIGINT PRIMARY KEY,
    title VARCHAR NOT NULL,
    priority VARCHAR NOT NULL,
    creation_date DATE NOT NULL,
    limit_date DATE NOT NULL,
    project_id BIGINT NOT NULL,
    user_id BIGINT,
    status VARCHAR NOT NULL,

    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (user_id, project_id) REFERENCES users_projects(user_id, project_id),

    CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH')),
    CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),
    CHECK (limit_date >= creation_date)
);


CREATE TABLE comments (
	id BIGINT PRIMARY KEY,
	content VARCHAR(255) NOT NULL,
	date DATE NOT NULL,
	user_id BIGINT NOT NULL,
	task_id BIGINT NOT NULL,

	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (task_id) REFERENCES tasks(id)
);


INSERT INTO users (id, name, surname1, surname2, email)
VALUES
    (1, 'Juan', 'García', 'López', 'juan.garcia@example.com'),
    (2, 'Ana', 'Martínez', NULL, 'ana.martinez@example.com'),
    (3, 'Pedro', 'Sánchez', 'Ruiz', 'pedro.sanchez@example.com');


INSERT INTO projects (id, name, description, creationDate)
VALUES
    (1, 'Web Corporativa', 'Desarrollo de la nueva web', '2026-09-01'),
    (2, 'App Móvil', 'Aplicación móvil de la empresa', '2026-09-02');


INSERT INTO users_projects (user_id, project_id, leader)
VALUES
    (1, 1, TRUE),
    (2, 1, FALSE),
    (2, 2, TRUE),
    (3, 2, FALSE);


INSERT INTO tasks (
    id, title, priority, creation_date, limit_date,
    project_id, user_id, status)
VALUES
    (1, 'Diseñar página principal', 'HIGH', '2026-09-03', '2026-09-10', 1, 1, 'IN_PROGRESS'),
    (2, 'Crear formulario de contacto', 'MEDIUM', '2026-09-03', '2026-09-12', 1, NULL, 'PENDING'),
    (3, 'Diseñar pantalla de login', 'HIGH', '2026-09-03', '2026-09-08', 2, 3, 'PENDING');


INSERT INTO comments (id, content, date, user_id, task_id)
VALUES
    (1, 'La estructura inicial ya está preparada.', '2026-09-04', 1, 1),
    (2, 'Necesitamos revisar el diseño antes de continuar.', '2026-09-04', 2, 1),
    (3, '¿Cuándo podemos empezar con esta tarea?', '2026-09-04', 3, 3);



SELECT * FROM users;
SELECT * FROM projects;
SELECT * FROM users_projects;
SELECT * FROM tasks;
SELECT * FROM comments;



INSERT INTO users (id, name, surname1, surname2, email)
VALUES (10, 'Carlos', 'López', 'García', 'juan.garcia@example.com');


INSERT INTO tasks (
    id, title, priority, creation_date, limit_date,
    project_id, user_id, status
)
VALUES (
    10, 'Prueba prioridad', 'URGENT', '2026-09-04', '2026-09-10',
    1, NULL, 'PENDING'
);


INSERT INTO tasks (
    id, title, priority, creation_date, limit_date,
    project_id, user_id, status
)
VALUES (
    11, 'Prueba estado', 'HIGH', '2026-09-04', '2026-09-10',
    1, NULL, 'FINISHED'
);


INSERT INTO tasks (
    id, title, priority, creation_date, limit_date,
    project_id, user_id, status
)
VALUES (
    12, 'Prueba fechas', 'HIGH', '2026-09-10', '2026-09-05',
    1, NULL, 'PENDING'
);


INSERT INTO tasks (
    id, title, priority, creation_date, limit_date,
    project_id, user_id, status
)
VALUES (
    13, 'Prueba usuario-proyecto', 'MEDIUM',
    '2026-09-04', '2026-09-10',
    1, 3, 'PENDING'
);


INSERT INTO comments (
    id, content, date, user_id, task_id
)
VALUES (
    10, 'Comentario de prueba', '2026-09-04', 9999, 1
);


INSERT INTO tasks (
    id, title, priority, creation_date, limit_date,
    project_id, user_id, status
)
VALUES (
    14, 'Prueba proyecto inexistente', 'LOW',
    '2026-09-04', '2026-09-10',
    9999, NULL, 'PENDING'
);