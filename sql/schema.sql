SET time_zone = '+00:00';

CREATE TABLE IF NOT EXISTS games (
    id INT UNSIGNED AUTO_INCREMENT,
    name TEXT NOT NULL,
    creation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT games_pk PRIMARY KEY(id)
);

CONSTRAINT TABLE IF NOT EXISTS games_advancements (
    id INT UNSIGNED AUTO_INCREMENT,
    advancement_name TEXT NOT NULL,
    advancement_description TEXT NOT NULL,
    game_id INT UNSIGNED NOT NULL,
    obtained DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT games_advancements_pk PRIMARY KEY(id),
    CONSTRAINT games_advancements_fk_game_id FOREIGN KEY(game_id) REFERENCES games(id)
);