import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap-icons/font/bootstrap-icons.css';
import NAVBAR from '../componantes/NAVBAR';

const ADMIN_REG = () => {
  const navigate = useNavigate();

  // State
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleAdminRegister = async (e) => {
    e.preventDefault();
    setError('');

    if (password !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(
        `${import.meta.env.VITE_FINAL_BASE_URL}/api/auth/admin/register`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            name,
            email,
            password,
          }),
          mode: 'cors',
        }
      );

      const data = await response.json();

      console.log('Admin register status:', response.status);
      console.log('Admin register data:', data);

      if (!response.ok) {
        throw new Error(data.message || 'Admin registration failed');
      }

      if (data.token) {
        localStorage.setItem('token', data.token);
      }

      navigate('/emp'); // or '/admin/login'
    } catch (err) {
      console.error('Admin register error:', err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <NAVBAR />
      <main
        className="d-flex justify-content-center align-items-center"
        style={{
          width: '100vw',
          marginLeft: 'calc(-50vw + 50%)',
          minHeight: '100vh',
          backgroundColor: '#f1f3f5',
        }}
      >
        <div
          className="container"
          style={{
            maxWidth: '1280px',
            margin: '0 auto',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '100%',
          }}
        >
          <form
            className="p-5 bg-white rounded-4 shadow-lg"
            style={{ width: '100%', maxWidth: '450px' }}
            onSubmit={handleAdminRegister}
          >
            <i
              className="bi bi-shield-lock-fill mb-4 d-block mx-auto"
              style={{ fontSize: '3rem', color: '#dc3545' }}
            ></i>

            <h1 className="h4 mb-4 fw-bold text-center">
              Admin Registration
            </h1>

            {error && (
              <p className="text-danger text-center mb-3">{error}</p>
            )}

            <div className="form-floating mb-3">
              <input
                type="text"
                className="form-control rounded-3"
                placeholder="Admin Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
              <label>Admin Name</label>
            </div>

            <div className="form-floating mb-3">
              <input
                type="email"
                className="form-control rounded-3"
                placeholder="admin@example.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
              <label>Email address</label>
            </div>

            <div className="form-floating mb-3">
              <input
                type="password"
                className="form-control rounded-3"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              <label>Password</label>
            </div>

            <div className="form-floating mb-4">
              <input
                type="password"
                className="form-control rounded-3"
                placeholder="Confirm Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
              />
              <label>Confirm Password</label>
            </div>

            <button
              type="submit"
              className="btn btn-danger w-100 py-2 rounded-3 mb-3"
              disabled={loading}
              style={{ transition: '0.3s' }}
            >
              {loading ? 'Creating admin...' : 'Create Admin'}
            </button>

            <p className="mt-4 mb-0 text-body-secondary text-center">
              © 2017–2025 Elite Cars Dealership
            </p>
          </form>
        </div>
      </main>
    </>
  );
};

export default ADMIN_REG;
