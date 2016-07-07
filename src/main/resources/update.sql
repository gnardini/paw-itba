ALTER TABLE users ADD lastLogin timestamp;
ALTER TABLE users ADD enabled boolean;
ALTER TABLE users ADD lastPasswordChange timestamp;
ALTER TABLE restaurant ADD createdDate timestamp;
ALTER TABLE restaurant ADD closedDate timestamp;
ALTER TABLE restaurant ADD closedReason varchar(150);

UPDATE users SET enabled = true;
UPDATE users SET lastLogin = CURRENT_TIMESTAMP - INTERVAL '1 day';
UPDATE users SET lastPasswordChange = CURRENT_TIMESTAMP - INTERVAL '1 day';
UPDATE restaurant SET createdDate = CURRENT_TIMESTAMP - INTERVAL '1 day';
UPDATE restaurant SET closedDate = CURRENT_TIMESTAMP - INTERVAL '1 day';

ALTER TABLE users ALTER COLUMN lastLogin SET NOT NULL;
ALTER TABLE users ALTER COLUMN lastPasswordChange SET NOT NULL;
ALTER TABLE restaurant ALTER COLUMN createdDate SET NOT NULL;