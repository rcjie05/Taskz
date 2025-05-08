-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2025 at 12:44 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `task`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_project`
--

CREATE TABLE `tbl_project` (
  `u_id` int(10) DEFAULT NULL,
  `p_id` int(10) NOT NULL,
  `p_name` varchar(255) NOT NULL,
  `u_fname` varchar(255) NOT NULL,
  `p_date` date NOT NULL,
  `p_duedate` date NOT NULL,
  `p_status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_task`
--

CREATE TABLE `tbl_task` (
  `t_id` int(11) NOT NULL,
  `p_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `p_name` varchar(255) NOT NULL,
  `u_fname` varchar(255) NOT NULL,
  `user_assign` varchar(255) NOT NULL,
  `t_date` date NOT NULL,
  `t_duedate` date NOT NULL,
  `t_status` varchar(255) NOT NULL,
  `accept` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `u_id` int(11) NOT NULL,
  `u_fname` varchar(255) NOT NULL,
  `u_lname` varchar(255) NOT NULL,
  `u_email` varchar(255) NOT NULL,
  `u_contact` varchar(255) NOT NULL,
  `u_gender` varchar(255) NOT NULL,
  `u_username` varchar(255) NOT NULL,
  `u_password` varchar(255) NOT NULL,
  `u_type` varchar(255) NOT NULL,
  `u_status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_project`
--
ALTER TABLE `tbl_project`
  ADD PRIMARY KEY (`p_id`),
  ADD KEY `user_id` (`u_id`);

--
-- Indexes for table `tbl_task`
--
ALTER TABLE `tbl_task`
  ADD PRIMARY KEY (`t_id`),
  ADD KEY `project_id` (`p_id`),
  ADD KEY `userid` (`u_id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`u_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_project`
--
ALTER TABLE `tbl_project`
  MODIFY `p_id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_task`
--
ALTER TABLE `tbl_task`
  MODIFY `t_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_project`
--
ALTER TABLE `tbl_project`
  ADD CONSTRAINT `user_id` FOREIGN KEY (`u_id`) REFERENCES `tbl_users` (`u_id`);

--
-- Constraints for table `tbl_task`
--
ALTER TABLE `tbl_task`
  ADD CONSTRAINT `project_id` FOREIGN KEY (`p_id`) REFERENCES `tbl_project` (`p_id`),
  ADD CONSTRAINT `userid` FOREIGN KEY (`u_id`) REFERENCES `tbl_users` (`u_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
