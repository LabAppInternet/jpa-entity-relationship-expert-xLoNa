CREATE TABLE User_lab (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(255) NOT NULL UNIQUE,
                          email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Note (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      content TEXT NOT NULL,
                      owner_id BIGINT NOT NULL,
                      creation_Date TIMESTAMP NOT NULL,
                      FOREIGN KEY (owner_id) REFERENCES User_lab(id)
);

CREATE TABLE Tag (
    name VARCHAR(255) PRIMARY KEY
);

CREATE TABLE Permission (
    user_Id BIGINT NOT NULL,
    note_Id BIGINT NOT NULL,
    can_Read BOOLEAN NOT NULL,
    can_Edit BOOLEAN NOT NULL,
    PRIMARY KEY (user_Id, note_Id),
    FOREIGN KEY (user_Id) REFERENCES User_lab(id),
    FOREIGN KEY (note_Id) REFERENCES Note(id)
);

CREATE TABLE Note_Tag (
    note_id BIGINT NOT NULL,
    tag_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (note_id, tag_name),
    FOREIGN KEY (note_id) REFERENCES Note(id),
    FOREIGN KEY (tag_name) REFERENCES Tag(name)
);
