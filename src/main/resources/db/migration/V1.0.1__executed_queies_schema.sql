CREATE SEQUENCE executed_queries_id_seq;

CREATE TABLE executed_queries (
  query TEXT,
  executed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
