CREATE TABLE `tenant_database_config` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `database_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `port` varchar(255) DEFAULT NULL,
  `server` varchar(255) DEFAULT NULL,
  `tenant` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8u88r9jmqd54htpnqjqhfxjed` (`tenant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
