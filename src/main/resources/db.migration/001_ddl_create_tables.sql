CREATE TABLE IF NOT EXISTS dialog_messages (
    from_user_id UUID NOT NULL,
    to_user_id UUID NOT NULL,
    text TEXT NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP
    );

SELECT create_distributed_table('dialog_messages', 'from_user_id');