CREATE INDEX idx_tasks__owner_id_id ON tasks(owner_id, id);

CREATE INDEX idx_tasks__owner_id_status_id ON tasks(owner_id, status, id);

CREATE INDEX idx_tasks__owner_id_due_date_id ON tasks(owner_id, due_date, id);