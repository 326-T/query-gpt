CREATE TABLE executed_queries (
  query TEXT,
  is_schema BOOLEAN,
  executed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
