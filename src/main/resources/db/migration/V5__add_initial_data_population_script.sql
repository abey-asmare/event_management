-- Insert default user
INSERT INTO users (id, name, email, role, created_at, updated_at)
SELECT '11111111-1111-1111-1111-111111111111', 'Admin', 'admin@example.com', 'ADMIN', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = '11111111-1111-1111-1111-111111111111');

-- Insert default organizer with the id
INSERT INTO organizers (id, organization_name, is_verified, created_at, updated_at)
SELECT '11111111-1111-1111-1111-111111111111', 'Default Org', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM organizers WHERE id = '11111111-1111-1111-1111-111111111111');

-- Insert default venue with a different fixed UUID
INSERT INTO venues (id, name, street, city, postal_code, country, capacity, created_at, updated_at)
SELECT '11111111-1111-1111-1111-111111111111', 'Default Venue', '123 Main St', 'Metropolis', '12345', 'USA', 100, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE id = '11111111-1111-1111-1111-111111111111');