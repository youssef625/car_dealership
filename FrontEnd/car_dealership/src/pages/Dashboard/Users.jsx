import React, { useState } from "react";
import UsersTable from "../../componantes/UsersTable";
import Pagination from "react-bootstrap/Pagination";

const Users = () => {
  // const [users, setUsers] = useState([
  //   {
  //     id: 1,
  //     name: "Admin User",
  //     email: "admin@cardealership.com",
  //     role: "superAdmin",
  //     createdAt: "2025-11-22T14:33:19.870342",
  //     banned: false,
  //   },
  //   {
  //     id: 2,
  //     name: "John Doe",
  //     email: "john@example.com",
  //     role: "admin",
  //     createdAt: "2025-11-23T09:15:00.000000",
  //     banned: false,
  //   },
  //   {
  //     id: 3,
  //     name: "Jane Smith",
  //     email: "jane@example.com",
  //     role: "user",
  //     createdAt: "2025-11-24T11:20:45.123456",
  //     banned: false,
  //   },
  //   {
  //     id: 4,
  //     name: "Blocked User",
  //     email: "blocked@example.com",
  //     role: "user",
  //     createdAt: "2025-11-25T08:00:00.000000",
  //     banned: true,
  //   },
  // ]);

  const [users, setUsers] = useState([
    {
      id: 1,
      name: "User 1",
      email: "user1@example.com",
      role: "user",
      createdAt: "2025-11-01T10:00:00",
      banned: false,
    },
    {
      id: 2,
      name: "User 2",
      email: "user2@example.com",
      role: "admin",
      createdAt: "2025-11-02T10:00:00",
      banned: false,
    },
    {
      id: 3,
      name: "User 3",
      email: "user3@example.com",
      role: "user",
      createdAt: "2025-11-03T10:00:00",
      banned: false,
    },
    {
      id: 4,
      name: "User 4",
      email: "user4@example.com",
      role: "user",
      createdAt: "2025-11-04T10:00:00",
      banned: true,
    },
    {
      id: 5,
      name: "User 5",
      email: "user5@example.com",
      role: "admin",
      createdAt: "2025-11-05T10:00:00",
      banned: false,
    },
    {
      id: 6,
      name: "User 6",
      email: "user6@example.com",
      role: "user",
      createdAt: "2025-11-06T10:00:00",
      banned: false,
    },
    {
      id: 7,
      name: "User 7",
      email: "user7@example.com",
      role: "user",
      createdAt: "2025-11-07T10:00:00",
      banned: false,
    },
    {
      id: 8,
      name: "User 8",
      email: "user8@example.com",
      role: "admin",
      createdAt: "2025-11-08T10:00:00",
      banned: false,
    },
    {
      id: 9,
      name: "User 9",
      email: "user9@example.com",
      role: "user",
      createdAt: "2025-11-09T10:00:00",
      banned: false,
    },
    {
      id: 10,
      name: "User 10",
      email: "user10@example.com",
      role: "user",
      createdAt: "2025-11-10T10:00:00",
      banned: true,
    },

    {
      id: 11,
      name: "User 11",
      email: "user11@example.com",
      role: "admin",
      createdAt: "2025-11-11T10:00:00",
      banned: false,
    },
    {
      id: 12,
      name: "User 12",
      email: "user12@example.com",
      role: "user",
      createdAt: "2025-11-12T10:00:00",
      banned: false,
    },
    {
      id: 13,
      name: "User 13",
      email: "user13@example.com",
      role: "user",
      createdAt: "2025-11-13T10:00:00",
      banned: false,
    },
    {
      id: 14,
      name: "User 14",
      email: "user14@example.com",
      role: "user",
      createdAt: "2025-11-14T10:00:00",
      banned: false,
    },
    {
      id: 15,
      name: "User 15",
      email: "user15@example.com",
      role: "admin",
      createdAt: "2025-11-15T10:00:00",
      banned: false,
    },
    {
      id: 16,
      name: "User 16",
      email: "user16@example.com",
      role: "user",
      createdAt: "2025-11-16T10:00:00",
      banned: true,
    },
    {
      id: 17,
      name: "User 17",
      email: "user17@example.com",
      role: "user",
      createdAt: "2025-11-17T10:00:00",
      banned: false,
    },
    {
      id: 18,
      name: "User 18",
      email: "user18@example.com",
      role: "admin",
      createdAt: "2025-11-18T10:00:00",
      banned: false,
    },
    {
      id: 19,
      name: "User 19",
      email: "user19@example.com",
      role: "user",
      createdAt: "2025-11-19T10:00:00",
      banned: false,
    },
    {
      id: 20,
      name: "User 20",
      email: "user20@example.com",
      role: "user",
      createdAt: "2025-11-20T10:00:00",
      banned: false,
    },

    {
      id: 21,
      name: "User 21",
      email: "user21@example.com",
      role: "user",
      createdAt: "2025-11-21T10:00:00",
      banned: false,
    },
    {
      id: 22,
      name: "User 22",
      email: "user22@example.com",
      role: "admin",
      createdAt: "2025-11-22T10:00:00",
      banned: false,
    },
    {
      id: 23,
      name: "User 23",
      email: "user23@example.com",
      role: "user",
      createdAt: "2025-11-23T10:00:00",
      banned: false,
    },
    {
      id: 24,
      name: "User 24",
      email: "user24@example.com",
      role: "user",
      createdAt: "2025-11-24T10:00:00",
      banned: true,
    },
    {
      id: 25,
      name: "User 25",
      email: "user25@example.com",
      role: "admin",
      createdAt: "2025-11-25T10:00:00",
      banned: false,
    },
    {
      id: 26,
      name: "User 26",
      email: "user26@example.com",
      role: "user",
      createdAt: "2025-11-26T10:00:00",
      banned: false,
    },
    {
      id: 27,
      name: "User 27",
      email: "user27@example.com",
      role: "user",
      createdAt: "2025-11-27T10:00:00",
      banned: false,
    },
    {
      id: 28,
      name: "User 28",
      email: "user28@example.com",
      role: "user",
      createdAt: "2025-11-28T10:00:00",
      banned: false,
    },
    {
      id: 29,
      name: "User 29",
      email: "user29@example.com",
      role: "admin",
      createdAt: "2025-11-29T10:00:00",
      banned: false,
    },
    {
      id: 30,
      name: "User 30",
      email: "user30@example.com",
      role: "user",
      createdAt: "2025-11-30T10:00:00",
      banned: false,
    },
  ]);

  
  const [currentPage, setCurrentPage] = useState(1);

  const usersPerPage = 10;

  const totalPages = Math.ceil(users.length / usersPerPage);

  const startIndex = (currentPage - 1) * usersPerPage;
  const selectedUsers = users.slice(startIndex, startIndex + usersPerPage);

  const handlePageChange = (page) => {
    if (page < 1 || page > totalPages) return;
    setCurrentPage(page);
  };

  const renderPaginationItems = () => {
    let items = [];

    for (let page = 1; page <= totalPages; page++) {
      items.push(
        <Pagination.Item
          key={page}
          active={page === currentPage}
          onClick={() => handlePageChange(page)}
        >
          {page}
        </Pagination.Item>
      );
    }

    return items;
  };

  return (
    <div className="mt-5">
      <h2 className="text-center text-danger">Users</h2>

      <UsersTable
        users={selectedUsers} 
        fullUsers={users} 
        setUsers={setUsers}
      />


      <Pagination className="d-flex justify-content-center mt-3">
        <Pagination.First onClick={() => handlePageChange(1)} />
        <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} />

        {renderPaginationItems()}

        <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} />
        <Pagination.Last onClick={() => handlePageChange(totalPages)} />
      </Pagination>
    </div>
  );
};

export default Users;
