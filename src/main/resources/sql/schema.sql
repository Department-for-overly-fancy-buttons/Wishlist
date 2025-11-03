DROP DATABASE IF EXISTS `wishlist_database_dept:ofb`;
CREATE DATABASE `wishlist_database_dept:ofb`
  DEFAULT CHARACTER SET utf8mb4;
USE `wishlist_database_dept:ofb`;


CREATE TABLE Accounts (
                          AccountId    INT NOT NULL AUTO_INCREMENT,
                          UserName VARCHAR(255) UNIQUE NOT NULL,
                          Password VARCHAR(255) NOT NULL,
                          PRIMARY KEY (AccountId)
);


CREATE TABLE Wishlists (
                           WishlistId         INT NOT NULL AUTO_INCREMENT,
                           Title       VARCHAR(255) NOT NULL,
                           OwnerId INT NOT NULL,
                           PRIMARY KEY (WishlistId),
                           FOREIGN KEY (OwnerId) REFERENCES Accounts (AccountId)
);


CREATE TABLE Wishes (
                        WishId INT NOT NULL AUTO_INCREMENT,
                        Name  VARCHAR(150) NOT NULL,
                        Description VARCHAR(500),
                        Url VARCHAR(1000),
                        WishlistId INT NOT NULL,
                        PRIMARY KEY (WishId, WishlistID),
                        FOREIGN KEY (WishlistId) REFERENCES WishLists (WishlistId)
                            ON DELETE CASCADE
);