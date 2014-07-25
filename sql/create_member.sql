CREATE TABLE `member` (
  `name` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `type` varchar(128) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
