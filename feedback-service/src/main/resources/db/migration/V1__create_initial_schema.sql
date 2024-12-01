CREATE TABLE questions (
    id UUID PRIMARY KEY,
    text VARCHAR(255) NOT NULL,
    content TEXT,
    category VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    answer_type VARCHAR(50),
    sentiment BOOLEAN DEFAULT false,
    required BOOLEAN DEFAULT false,
    active BOOLEAN DEFAULT true,
    project_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE question_options (
    id UUID PRIMARY KEY,
    text VARCHAR(255) NOT NULL,
    value INTEGER,
    question_id UUID REFERENCES questions(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE feedbacks (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL,
    user_id UUID NOT NULL,
    question_set_id UUID NOT NULL,
    title VARCHAR(255),
    description TEXT,
    category VARCHAR(50),
    privacy_level VARCHAR(50),
    rating DOUBLE PRECISION,
    additional_comments TEXT,
    submitted_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE answers (
    id UUID PRIMARY KEY,
    feedback_id UUID REFERENCES feedbacks(id) ON DELETE CASCADE,
    type VARCHAR(50),
    rating_value INTEGER,
    text_value TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
); 