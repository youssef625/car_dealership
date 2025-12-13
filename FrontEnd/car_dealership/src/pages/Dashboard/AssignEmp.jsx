import { useState } from "react";
import RequestsTable from "../../componantes/RequestsTable";
const AssignEmp = () => {
  const [requests, setRequests] = useState([
    {
      id: 1,
      name: "Ahmed Attia",
      email: "ahmed@example.com",
      password: "123456",
    },
    {
      id: 2,
      name: "Hassan Mohamed",
      email: "hassan@example.com",
      password: "password123",
    },
    {
      id: 3,
      name: "Mostafa Ali",
      email: "mostafa@example.com",
      password: "mypassword",
    },
    {
      id: 4,
      name: "Yousef Nader",
      email: "yousef@example.com",
      password: "qwerty123",
    },
    {
      id: 5,
      name: "Abdallah Mahmoud",
      email: "abdallah@example.com",
      password: "pass789",
    },
  ]);
  // function deleteRequest(request){
  // }
  function handleRequests(request) {
    console.log(request); //waiting for fetch
    setRequests((prev) => prev.filter((r) => r.id !== request.id));
  }
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
