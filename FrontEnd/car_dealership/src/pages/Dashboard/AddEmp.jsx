import React, { useState } from 'react';
import ADMIN_NAV from '../../componantes/ADMIN_NAV.jsx';

const AddEmp = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // For now, just log the values
    console.log('Username:', username);
    console.log('Password:', password);
    // You can replace this with API call to add employee
  };

  return (
    <div className="d-flex">
      {/* Sidebar */}
      <ADMIN_NAV />

      {/* Main content */}
      <div style={{ marginLeft: '260px', width: '100%' }}>
        <main className="p-4" style={{ marginTop: '70px' }}>
          <h2>Add Employee</h2>
          <form
            onSubmit={handleSubmit}
            style={{ maxWidth: '400px', marginTop: '20px' }}
          >
            <div className="mb-3">
              <label htmlFor="username" className="form-label">
                Username
              </label>
              <input
                type="text"
                className="form-control"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                Password
              </label>
              <input
                type="password"
                className="form-control"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            <button type="submit" className="btn btn-primary">
              Add Employee
            </button>
          </form>
        </main>
      </div>
    </div>
  );
};

export default AddEmp;
