-- Groupes d'utilisateurs
CREATE TABLE IF NOT EXISTS groups (
    id SERIAL PRIMARY KEY,
    group_name VARCHAR(255)
);

-- Utilisateurs
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    group_id BIGINT,
    company_name VARCHAR,
    company_logo TEXT,
    company_address TEXT,
    settings JSONB,             -- monnaie, langue, template par défaut, TVA incluse/excl.
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE SET NULL
);

-- Clients
CREATE TABLE IF NOT EXISTS clients (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR NOT NULL,
    email VARCHAR,
    company VARCHAR,
    billing_address TEXT,
    meta JSONB,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- Unités
CREATE TABLE IF NOT EXISTS units (
    id SERIAL PRIMARY KEY,
    code VARCHAR UNIQUE,
    label VARCHAR
);

-- Catégories de services
CREATE TABLE IF NOT EXISTS service_categories (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR NOT NULL
);

-- Taxes
CREATE TABLE IF NOT EXISTS taxes (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR,
    rate NUMERIC(5,2) NOT NULL
);

-- Services
CREATE TABLE IF NOT EXISTS services (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    category_id INT REFERENCES service_categories(id),
    title VARCHAR NOT NULL,
    description TEXT,
    unit_id INT REFERENCES units(id),
    base_price NUMERIC(12,2) NOT NULL,
    default_tax_id INT REFERENCES taxes(id),
    active BOOLEAN DEFAULT TRUE,
    meta JSONB,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- Conteneur d'offres
CREATE TABLE IF NOT EXISTS offers (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE SET NULL,
    client_id INT REFERENCES clients(id),
    reference VARCHAR,
    status VARCHAR,            -- draft, sent, accepted, rejected
    accepted_version_id INT,
    sent_at TIMESTAMP,
    accepted_at TIMESTAMP,
    rejected_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- Versions d'offre
CREATE TABLE IF NOT EXISTS offer_versions (
    id SERIAL PRIMARY KEY,
    offer_id INT REFERENCES offers(id) ON DELETE CASCADE,
    version_number INT NOT NULL,
    title VARCHAR,
    currency VARCHAR(3) NOT NULL,
    valid_until DATE,
    notes TEXT,
    discount_pct NUMERIC(5,2),
    discount_amt NUMERIC(12,2),
    tax_inclusive BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    UNIQUE (offer_id, version_number)
);

-- Ajout de la contrainte FK circulaire après la création d'offer_versions
ALTER TABLE offers
    ADD CONSTRAINT fk_accepted_version
    FOREIGN KEY (accepted_version_id) REFERENCES offer_versions(id);

-- Sections dans une version d'offre
CREATE TABLE IF NOT EXISTS offer_sections (
    id SERIAL PRIMARY KEY,
    offer_version_id INT REFERENCES offer_versions(id) ON DELETE CASCADE,
    parent_section_id INT REFERENCES offer_sections(id),
    title VARCHAR NOT NULL,
    position INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- Lignes d'offre
CREATE TABLE IF NOT EXISTS offer_items (
    id SERIAL PRIMARY KEY,
    section_id INT REFERENCES offer_sections(id) ON DELETE CASCADE,
    service_id INT REFERENCES services(id),
    title VARCHAR NOT NULL,
    description TEXT,
    unit_id INT REFERENCES units(id),
    quantity NUMERIC(12,2) NOT NULL DEFAULT 1,
    unit_price NUMERIC(12,2) NOT NULL,
    discount_pct NUMERIC(5,2),
    tax_id INT REFERENCES taxes(id),
    price_source VARCHAR,          -- 'service_base' | 'pricelist' | 'override' | 'bundle'
    position INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- Documents générés
CREATE TABLE IF NOT EXISTS offer_documents (
    id SERIAL PRIMARY KEY,
    offer_version_id INT REFERENCES offer_versions(id) ON DELETE CASCADE,
    format VARCHAR NOT NULL,
    storage_url TEXT NOT NULL,
    is_official BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT now()
);
