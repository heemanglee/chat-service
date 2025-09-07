ALTER TABLE chat_room
    ADD CONSTRAINT chk_member_limit
        CHECK (member_limit >= 1 AND member_limit <= 100);