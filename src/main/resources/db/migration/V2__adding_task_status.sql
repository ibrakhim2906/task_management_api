ALTER TABLE tasks ADD COLUMN status VARCHAR(20);

UPDATE tasks SET status='DONE' WHERE completed=true;
UPDATE tasks SET status='TODO' WHERE completed=false;

ALTER TABLE tasks DROP COLUMN completed;

ALTER TABLE tasks ALTER COLUMN status SET NOT NULL;
