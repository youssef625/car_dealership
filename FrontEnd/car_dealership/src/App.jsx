import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';

import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

// Pages
import HOME from './pages/HOME';
import LOGIN from './pages/LOGIN';
import SIGNUP from './pages/SIGNUP';
import PRODUCTES from './pages/PRODUCTES';
import PRODUCT_PAGE from './pages/PRODUCT_PAGE';
import ABOUTUS from './pages/ABOUTUS';
import CONTACTUS from './pages/CONTACTUS';
import ADMIN_REG from './pages/ADMIN_REG';

// Admin
import ADMIN from './componantes/ADMIN_NAV';
import AddEmp from './pages/Dashboard/AddEmp';

// Private route for regular protected pages
const PrivateRoute = ({ children }) => {
  const token = localStorage.getItem('token');
  return token ? children : <Navigate to="/login" replace />;
};

// Special route for admin register
const AdminRegisterRoute = ({ children }) => {
  const token = localStorage.getItem('token');

  // Allow access if token exists
  if (token) return children;

  // Allow first admin registration
  const isFirstAdmin = true; // Replace later with backend check
  if (isFirstAdmin) return children;

  return <Navigate to="/login" replace />;
};

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Public */}
        <Route path="/" element={<HOME />} />
        <Route path="/login" element={<LOGIN />} />
        <Route path="/signup" element={<SIGNUP />} />
        <Route path="/products" element={<PRODUCTES />} />
        <Route path="/car/:id" element={<PRODUCT_PAGE />} />
        <Route path="/about" element={<ABOUTUS />} />
        <Route path="/contact" element={<CONTACTUS />} />

        {/* Admin */}
        <Route
          path="/admin"
          element={
            <PrivateRoute>
              <ADMIN />
            </PrivateRoute>
          }
        />

        <Route
          path="/admin/register"
          element={
            <AdminRegisterRoute>
              <ADMIN_REG />
            </AdminRegisterRoute>
          }
        />

        <Route
          path="/admin/add-employee"
          element={
            <PrivateRoute>
              <AddEmp />
            </PrivateRoute>
          }
        />

        {/* Fallback */}
        <Route path="*" element={<Navigate to="/" replace />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
