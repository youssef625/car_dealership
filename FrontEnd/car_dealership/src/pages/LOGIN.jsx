import React, { useState } from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css';
import NAVBAR from '../componantes/NAVBAR';
import { GoogleLogin } from '@react-oauth/google';
import { useNavigate } from 'react-router-dom';

const LOGIN = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

  const handleTraditionalLogin = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem('token', data.token);
        localStorage.setItem('userEmail', data.email);
        localStorage.setItem('userName', data.name);
        localStorage.setItem('userRole', data.role);
        navigate('/');
      } else {
        setError('Invalid email or password');
      }
    } catch (err) {
      setError('An error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleGoogleSuccess = async (credentialResponse) => {
    setError('');
    setLoading(true);

    try {
      const response = await fetch(`${API_BASE_URL}/api/auth/google`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ idToken: credentialResponse.credential }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem('token', data.token);
        localStorage.setItem('userEmail', data.email);
        localStorage.setItem('userName', data.name);
        localStorage.setItem('userRole', data.role);
        navigate('/');
      } else {
        const errorText = await response.text();
        setError(errorText || 'Google sign-in failed');
      }
    } catch (err) {
      setError('An error occurred during Google sign-in. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleGoogleError = () => {
    setError('Google sign-in was cancelled or failed');
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
            style={{ width: '100%', maxWidth: '400px' }}
            onSubmit={handleTraditionalLogin}
          >
            {/* Car icon */}
            <i
              className="bi bi-car-front-fill mb-4 d-block mx-auto"
              style={{ fontSize: '3rem', color: '#0d6efd' }}
            ></i>

            <h1 className="h4 mb-4 fw-bold text-center">Sign in to your account</h1>

            {error && (
              <div className="alert alert-danger py-2" role="alert">
                {error}
              </div>
            )}

            <div className="form-floating mb-3">
              <input
                type="email"
                className="form-control rounded-3"
                id="floatingInput"
                placeholder="name@example.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
              <label htmlFor="floatingInput">Email address</label>
            </div>

            <div className="form-floating mb-3">
              <input
                type="password"
                className="form-control rounded-3"
                id="floatingPassword"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              <label htmlFor="floatingPassword">Password</label>
            </div>

            <div className="form-check text-start mb-4">
              <input
                className="form-check-input"
                type="checkbox"
                value="remember-me"
                id="checkDefault"
              />
              <label className="form-check-label" htmlFor="checkDefault">
                Remember me
              </label>
            </div>

            <button
              className="btn btn-primary w-100 py-2 rounded-3 mb-3"
              type="submit"
              disabled={loading}
              style={{ transition: '0.3s' }}
              onMouseOver={e => (e.currentTarget.style.backgroundColor = '#0b5ed7')}
              onMouseOut={e => (e.currentTarget.style.backgroundColor = '#0d6efd')}
            >
              {loading ? 'Signing in...' : 'Sign in'}
            </button>

            {/* Divider */}
            <div className="d-flex align-items-center my-3">
              <hr className="flex-grow-1" />
              <span className="px-3 text-muted small">OR</span>
              <hr className="flex-grow-1" />
            </div>

            {/* Google Sign-In Button */}
            <div className="d-flex justify-content-center">
              <GoogleLogin
                onSuccess={handleGoogleSuccess}
                onError={handleGoogleError}
                useOneTap
                theme="outline"
                size="large"
                text="signin_with"
                width="100%"
              />
            </div>

            <p className="mt-5 mb-0 text-body-secondary text-center">
              © 2017–2025 Elite Cars Dealership
            </p>
          </form>
        </div>
      </main>
    </>
  );
};

export default LOGIN;
