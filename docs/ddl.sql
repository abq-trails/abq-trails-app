CREATE TABLE IF NOT EXISTS `Trail`
(
    `trail_id`               INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `cabq_id`                INTEGER                           NOT NULL,
    `trail_name`             TEXT,
    `trail_length`           REAL                              NOT NULL,
    `trail_rating`           REAL                              NOT NULL,
    `trail_head_coordinates` TEXT,
    `bike_trail`             INTEGER                           NOT NULL,
    `horse_trail`            INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX `index_Trail_cabq_id` ON `Trail` (`cabq_id`);