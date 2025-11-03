-- V1__init.sql

-- extensões úteis
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Tabelas básicas

CREATE TABLE IF NOT EXISTS users (
   id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
   email varchar(255) not null unique,
   password_hash varchar (255) not null,
   full_name varchar(255),
   active boolean NOT NULL DEFAULT true,
   created_at timestamptz NOT NULL DEFAULT now(),
   updated_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS roles (
    id serial PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id uuid NOT NULL,
    role_id int NOT NULL,
    assigned_at timestamptz NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS teams (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(200) NOT NULL ,
    owner_id uuid NOT NULL ,
    created_at timestamptz NOT NULL DEFAULT now(),
    CONSTRAINT fk_team_owner FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS team_members (
    team_id uuid not null,
    user_id uuid not null,
    role varchar(100) default 'member',
    joined_at timestamptz not null default now(),
    primary key (team_id, user_id),
    constraint fk_tm_team foreign key (team_id) references teams(id) on delete cascade,
    constraint fk_tm_user foreign key (user_id) references users(id) on delete cascade
);

-- enum replacements simple strings com check

CREATE TABLE  if not exists tasks (
    id uuid primary key default gen_random_uuid(),
    title varchar(300) not null,
    description text,
    status varchar(50) not null default 'TODO', -- TOOD, IN_PROGRESS, DONE, BLOCK
    priority varchar(20) not null default 'MEDIUM', -- LOW, MEDIUM, HIGH, URGENT
    reporter_id uuid not null,
    assignee_id uuid,
    team_id uuid,
    due_data date,
    metadata jsonb default '{}'::jsonb,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint fk_task_reporter foreign key (reporter_id) references users(id) on delete cascade,
    constraint fk_task_assignee foreign key (assignee_id) references users(id) on delete cascade ,
    constraint fk_task_team foreign key  (team_id) references  teams(id) on delete cascade
);

CREATE TABLE IF NOT EXISTS task_history (
    id bigserial primary key,
    task_id uuid not null,
    changed_by uuid,
    change_type varchar(100) not null,
    old_value text,
    new_value text,
    created_at timestamptz not null default now(),
    constraint fk_th_task foreign key (task_id) references tasks(id) on delete cascade,
    constraint fk_th_user foreign key (changed_by) references users(id) on delete cascade
);

CREATE TABLE IF NOT EXISTS comments (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null,
    author_id uuid not null,
    content text not null,
    created_at timestamptz not null default now(),
    constraint fk_c_task foreign key (task_id) references tasks(id) on delete cascade ,
    constraint fk_c_user foreign key (author_id) references users(id) on delete cascade
);

create table if not exists attachments (
    id uuid primary key default gen_random_uuid(),
    task_id uuid not null,
    filename varchar(512) not null,
    url text not null,
    uploaded_by uuid,
    created_at timestamptz not null default now(),
    constraint fk_a_task foreign key (task_id) references tasks(id) on delete cascade
);

CREATE TABLE if not exists notifications (
    id uuid primary key default gen_random_uuid(),
    user_id uuid not null,
    type varchar(100) not null,
    payload jsonb,
    read boolean not null default false,
    created_at timestamptz not null default now(),
    constraint fk_n_user foreign key (user_id) references users(id) on delete cascade
);

CREATE TABLE IF NOT EXISTS audit_logs (
    id bigserial primary key ,
    entity varchar(100) not null,
    entity_id uuid,
    action varchar(100) not null,
    payload jsonb,
    created_at timestamptz not null default now()
);

-- Índices úteis

CREATE INDEX IF NOT EXISTS idx_tasks_assignee ON tasks(assignee_id);
CREATE INDEX IF NOT EXISTS idx_tasks_team ON tasks(team_id);
CREATE INDEX IF NOT EXISTS idx_tasks_status_priority ON tasks(status, priority);
CREATE INDEX IF NOT EXISTS id_comments_task ON comments(task_id);