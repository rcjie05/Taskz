-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 16, 2025 at 09:42 AM
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
-- Table structure for table `otp_requests`
--

CREATE TABLE `otp_requests` (
  `id` int(11) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `otp_code` varchar(10) NOT NULL,
  `expiry_time` datetime NOT NULL,
  `is_used` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_logs`
--

CREATE TABLE `tbl_logs` (
  `l_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `l_action` varchar(255) NOT NULL,
  `l_timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_project`
--

CREATE TABLE `tbl_project` (
  `u_id` int(11) NOT NULL,
  `p_id` int(11) NOT NULL,
  `p_name` varchar(255) NOT NULL,
  `p_description` varchar(255) NOT NULL,
  `u_fname` varchar(255) NOT NULL,
  `p_date` date NOT NULL,
  `p_duedate` date NOT NULL,
  `p_status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_project`
--

INSERT INTO `tbl_project` (`u_id`, `p_id`, `p_name`, `p_description`, `u_fname`, `p_date`, `p_duedate`, `p_status`) VALUES
(1, 1, 'Project 1', '', 'Rcjie', '2025-05-09', '2025-05-10', 'Active'),
(1, 2, 'Project 2', '', 'Rcjie', '2025-05-09', '2025-05-17', 'Active'),
(1, 3, 'Project 3', '', 'Rcjie', '2025-05-09', '2025-05-17', 'Active'),
(1, 4, 'Trial', 'pagbuhat ug unsa', 'Rcjie', '2025-05-07', '2025-05-24', 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_task`
--

CREATE TABLE `tbl_task` (
  `u_id` int(11) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  `t_id` int(11) NOT NULL,
  `p_name` varchar(255) DEFAULT NULL,
  `u_fname` varchar(255) DEFAULT NULL,
  `user_assign` varchar(255) NOT NULL,
  `t_date` date NOT NULL,
  `t_duedate` date NOT NULL,
  `t_status` varchar(255) NOT NULL,
  `accept` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_task`
--

INSERT INTO `tbl_task` (`u_id`, `p_id`, `t_id`, `p_name`, `u_fname`, `user_assign`, `t_date`, `t_duedate`, `t_status`, `accept`) VALUES
(1, 1, 1, 'Project 1', 'Rcjie', 'Dave', '2025-05-09', '2025-05-17', 'Active', 'Accepted'),
(1, 2, 2, 'Project 2', 'Rcjie', 'Dave', '2025-05-09', '2025-05-31', 'Active', 'Accepted'),
(1, 3, 3, 'Project 3', 'Rcjie', '', '2025-05-09', '2025-05-24', 'Active', NULL),
(1, 1, 4, 'Project 1', 'Rcjie', 'Anthony', '2025-05-14', '2025-05-30', 'Active', 'Accepted'),
(1, 4, 5, 'Trial', 'Rcjie', 'Anthony', '2025-05-14', '2025-05-24', 'Active', 'Accepted');

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
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_contact`, `u_gender`, `u_username`, `u_password`, `u_type`, `u_status`) VALUES
(1, 'Rcjie', 'Villena', 'weakpointz05@gmail.com', '09123456789', 'Male', 'admin', 'rcjie123', 'ADMIN', 'Active'),
(2, 'Dave', 'Tupas', 'godzdemonz05@gmail.com', '09987654321', 'Male', 'dave', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', 'USER', 'Active'),
(3, 'Anthony', 'Teves', 'mike@stcecilia.edu.ph', '09123456789', 'Male', 'teves', '0755ba9f152a6620a080afe71d32cd8e4b743438f06df94cc58c5a156340f744', 'USER', 'Active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `otp_requests`
--
ALTER TABLE `otp_requests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  ADD PRIMARY KEY (`l_id`),
  ADD KEY `user` (`u_id`);

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
  ADD KEY `projectid` (`p_id`),
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
-- AUTO_INCREMENT for table `otp_requests`
--
ALTER TABLE `otp_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  MODIFY `l_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_project`
--
ALTER TABLE `tbl_project`
  MODIFY `p_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_task`
--
ALTER TABLE `tbl_task`
  MODIFY `t_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  ADD CONSTRAINT `user` FOREIGN KEY (`u_id`) REFERENCES `tbl_users` (`u_id`);

--
-- Constraints for table `tbl_project`
--
ALTER TABLE `tbl_project`
  ADD CONSTRAINT `user_id` FOREIGN KEY (`u_id`) REFERENCES `tbl_users` (`u_id`);

--
-- Constraints for table `tbl_task`
--
ALTER TABLE `tbl_task`
  ADD CONSTRAINT `projectid` FOREIGN KEY (`p_id`) REFERENCES `tbl_project` (`p_id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `userid` FOREIGN KEY (`u_id`) REFERENCES `tbl_users` (`u_id`) ON DELETE SET NULL ON UPDATE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
