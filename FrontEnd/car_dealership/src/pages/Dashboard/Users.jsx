import React, { useEffect, useState } from "react";
import UsersTable from "../../componantes/UsersTable";
import Pagination from "react-bootstrap/Pagination";
import Button from "react-bootstrap/Button";

const Users = () => {
  const [users, setUsers] = useState([]); // المستخدمين للعرض
  const [fullUsers, setFullUsers] = useState([]); // كل المستخدمين للـ state الداخلي
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const pageSize = 10;

  const fetchUsers = async (page) => {
    const token = localStorage.getItem("token");
    if (!token) {
      alert("You must login first.");
      setLoading(false);
      return;
    }

    try {
      setLoading(true);
      const res = await fetch(
        `${
          import.meta.env.VITE_FINAL_BASE_URL
        }/api/users?page=${page}&size=${pageSize}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (!res.ok) throw new Error("Failed to fetch users");
      const data = await res.json();

      setUsers(data.content);
      setFullUsers(data.content); // كل البيانات موجودة للـ ban/unban
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Fetch error:", error);
      alert("Error fetching users");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers(currentPage);
  }, [currentPage]);

  if (loading)
    return (
      <div className="text-center mt-5 text-danger">
        <p>Loading users...</p>
      </div>
    );

  return (
    <div className="mt-5">
      <h2 className="text-center text-danger">Manage Users</h2>
      <UsersTable users={users} fullUsers={fullUsers} setUsers={setUsers} />

      {/* Pagination */}
      <div className="d-flex justify-content-center gap-2 mt-3">
        <Button
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 0))}
          disabled={currentPage === 0}
        >
          Previous
        </Button>
        <span className="align-self-center">
          Page {currentPage + 1} of {totalPages}
        </span>
        <Button
          onClick={() =>
            setCurrentPage((prev) => Math.min(prev + 1, totalPages - 1))
          }
          disabled={currentPage === totalPages - 1}
        >
          Next
        </Button>
      </div>
    </div>
  );
};

export default Users;
