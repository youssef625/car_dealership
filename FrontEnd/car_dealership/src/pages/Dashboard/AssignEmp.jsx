import { useState, useEffect } from "react";
import RequestsTable from "../../componantes/RequestsTable";

const AssignEmp = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRequests = async () => {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("You must login first.");
        setLoading(false);
        return;
      }

      try {
        const res = await fetch(
          `${
            import.meta.env.VITE_FINAL_BASE_URL
          }/api/users/unapproved-employees`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!res.ok) throw new Error("Failed to fetch requests");

        const data = await res.json();
        setRequests(data);
      } catch (error) {
        console.error("Fetch error:", error);
        alert("Error fetching requests");
      } finally {
        setLoading(false);
      }
    };

    fetchRequests();
  }, []);

  async function handleRequests(request) {
    const token = localStorage.getItem("token");
    if (!token) {
      alert("You must login first.");
      return;
    }

    try {
      const res = await fetch(
        `${import.meta.env.VITE_FINAL_BASE_URL}/api/users/approve/${
          request.id
        }`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) throw new Error("Failed to approve user");

      const data = await res.json();
      console.log("Approved:", data);

      setRequests((prev) => prev.filter((r) => r.id !== request.id));
      handleRequests(request);
    } catch (error) {
      console.error("Approval error:", error);
      alert("Error approving user");
    }
  }

  if (loading)
    return (
      <div className="text-center mt-5 text-danger">
        <p>Loading requests...</p>
      </div>
    );
  return (
    <div className="mt-5">
      <h2 className="text-center text-danger">Assign employees</h2>
      <RequestsTable requests={requests} handleRequests={handleRequests} />
    </div>
  );
};
// const [username, setUsername] = useState('');
// const [password, setPassword] = useState('');
// const handleSubmit = (e) => {
//   // e.preventDefault();
//   // // For now, just log the values
//   // console.log('Username:', username);
//   // console.log('Password:', password);
//   // You can replace this with API call to add employee
// };

//  <h2>Add Employee</h2>
//           <form
//             onSubmit={handleSubmit}
//             style={{ maxWidth: '400px', marginTop: '20px' }}
//           >
//             <div className="mb-3">
//               <label htmlFor="username" className="form-label">
//                 Username
//               </label>
//               <input
//                 type="text"
//                 className="form-control"
//                 id="username"
//                 value={username}
//                 onChange={(e) => setUsername(e.target.value)}
//                 required
//               />
//             </div>

//             <div className="mb-3">
//               <label htmlFor="password" className="form-label">
//                 Password
//               </label>
//               <input
//                 type="password"
//                 className="form-control"
//                 id="password"
//                 value={password}
//                 onChange={(e) => setPassword(e.target.value)}
//                 required
//               />
//             </div>

//             <button type="submit" className="btn btn-primary">
//               Add Employee
//             </button>
//           </form>
export default AssignEmp;
