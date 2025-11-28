import './App.css';
// Bootstrap CSS + JS
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

// Bootstrap Icons
import 'bootstrap-icons/font/bootstrap-icons.css';

import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

// Pages
import HOME from './pages/HOME';
import LOGIN from './pages/LOGIN';
import SIGNUP from './pages/SIGNUP';
import PRODUCTES from './pages/PRODUCTES';
import PRODUCT_PAGE from './pages/PRODUCT_PAGE';
import ABOUTUS from './pages/ABOUTUS';
import CONTACTUS from './pages/CONTACTUS';
import ADMIN from './componantes/ADMIN_NAV';
import AddEmp from './pages/Dashboard/AddEmp';
// Components
import NAVBAR from './componantes/NAVBAR';

function App() {
  return (
    <BrowserRouter>
     

        <Routes>
          <Route path="/" element={<HOME />} />
          <Route path="/login" element={<LOGIN />} />
          <Route path="/signup" element={<SIGNUP />} />
          <Route path="/products" element={<PRODUCTES />} />
          <Route path="/car/:id" element={<PRODUCT_PAGE />} />
          <Route path="/about" element={<ABOUTUS />} />
          <Route path="/contact" element={<CONTACTUS />} />
          <Route path="/admin" element={<ADMIN />} />
          <Route path="/admin/add-employee" element={<AddEmp />} />
        </Routes>
    </BrowserRouter>
  );
}

export default App;
