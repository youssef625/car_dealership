import "./App.css";

import { Route, Navigate, BrowserRouter, Routes } from "react-router-dom";
import HOME from "./pages/HOME";
import LOGIN from "./pages/LOGIN";
import SIGNUP from "./pages/SIGNUP";
import PRODUCTES from "./pages/PRODUCTES";
import PRODUCT_PAGE from "./pages/PRODUCT_PAGE";
import ABOUTUS from "./pages/ABOUTUS";
import CONTACTUS from "./pages/CONTACTUS";
import ADMIN_REG from "./pages/ADMIN_REG";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap-icons/font/bootstrap-icons.css";
import React, { useState } from "react";
import ADMIN_NAV from "./componantes/ADMIN_NAV.jsx";
import EditCar from "./pages/Dashboard/EditCar";
import AssignEmp from "./pages/Dashboard/AssignEmp.jsx";
import DeleteCar from "./pages/Dashboard/DeleteCar.jsx";
import Offers from "./pages/Dashboard/Offers.jsx";
import Users from "./pages/Dashboard/Users.jsx";
import EmpLayout from "./pages/employee/EmpLayout.jsx";
import LoginAdminEmp from "./pages/Dashboard/LoginAdminEmp.jsx";
import SignUpEmp from "./pages/employee/SignUpEmp.jsx";
import ADMIN from "./pages/Dashboard/ADMIN.jsx";
const PrivateRoute = ({ children }) => {
  const token = localStorage.getItem("token");
  return token ? children : <Navigate to="/login" replace />;
};

// Special route for admin register
const AdminRegisterRoute = ({ children }) => {
  const token = localStorage.getItem("token");

  // Allow access if token exists
  if (token) return children;

  // Allow first admin registration
  const isFirstAdmin = true; // Replace later with backend check
  if (isFirstAdmin) return children;

  return <Navigate to="/login" replace />;
};
function App() {
  const [cars, setCars] = useState([]);
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
        <Route
          path="/admin/register"
          element={
            <AdminRegisterRoute>
              <ADMIN_REG />
            </AdminRegisterRoute>
          }
        />
        {/*Admin*/}
        <Route
          path="/admin/*"
          element={
            <PrivateRoute>
              <ADMIN_NAV />
            </PrivateRoute>
          }
        >
          <Route index element={<ADMIN />} />

          <Route
            path="assignEmp"
            element={
              <PrivateRoute>
                <AssignEmp />
              </PrivateRoute>
            }
          />

          <Route
            path="editCar"
            element={
              <PrivateRoute>
                <EditCar setCars={setCars} cars={cars} />
              </PrivateRoute>
            }
          />
          <Route
            path="deleteCar"
            element={
              <PrivateRoute>
                <DeleteCar />
              </PrivateRoute>
            }
          />
          <Route
            path="offers"
            element={
              <PrivateRoute>
                <Offers />
              </PrivateRoute>
            }
          />
          <Route
            path="users"
            element={
              <PrivateRoute>
                <Users />
              </PrivateRoute>
            }
          />
        </Route>
        {/*employee*/}
        <Route
          path="/emp/*"
          element={
            <PrivateRoute>
              <EmpLayout />
            </PrivateRoute>
          }
        >
          <Route index element={<ADMIN />} />
          <Route
            path="editCar"
            element={
              <PrivateRoute>
                <EditCar setCars={setCars} cars={cars} />
              </PrivateRoute>
            }
          />
          <Route
            path="deleteCar"
            element={
              <PrivateRoute>
                <DeleteCar  />
              </PrivateRoute>
            }
          />
          <Route
            path="offers"
            element={
              <PrivateRoute>
                <Offers />
              </PrivateRoute>
            }
          />
        </Route>
        {/* Fallback */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
