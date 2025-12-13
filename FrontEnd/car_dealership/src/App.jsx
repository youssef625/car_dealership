import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap-icons/font/bootstrap-icons.css";
import React, { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import HOME from "./pages/HOME";
import LOGIN from "./pages/LOGIN";
import SIGNUP from "./pages/SIGNUP";
import PRODUCTES from "./pages/PRODUCTES";
import PRODUCT_PAGE from "./pages/PRODUCT_PAGE";
import ABOUTUS from "./pages/ABOUTUS";
import CONTACTUS from "./pages/CONTACTUS";
import ADMIN_NAV from "./componantes/ADMIN_NAV.jsx";
import EditCar from "./pages/Dashboard/EditCar";
import AssignEmp from "./pages/Dashboard/AssignEmp.jsx";
import DeleteCar from "./pages/Dashboard/DeleteCar.jsx";
import Offers from "./pages/Dashboard/Offers.jsx";
import Users from "./pages/Dashboard/Users.jsx";
import EmpLayout from "./pages/employee/EmpLayout.jsx";
import LoginAdminEmp from "./pages/Dashboard/LoginAdminEmp.jsx";
import SignUpAdmin from "./pages/Dashboard/SignUpAdmin.jsx";
import SignUpEmp from "./pages/employee/SignUpEmp.jsx";
import ADMIN from "./pages/Dashboard/ADMIN.jsx";

function App() {
 const [cars, setCars] = useState([]);
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
        <Route path="/admin/login" element={<LoginAdminEmp />} />
        <Route path="/emp/login" element={<LoginAdminEmp />} />
        <Route path="/admin/signup" element={<SignUpAdmin />} />
        <Route path="/emp/signup" element={<SignUpEmp />} />
        {/*Admin*/}
        <Route path="/admin/*" element={<ADMIN_NAV />}>
          <Route index element={<ADMIN />} />

          <Route path="assignEmp" element={<AssignEmp />} />
          <Route
            path="editCar"
            element={<EditCar setCars={setCars} cars={cars} />}
          />
          <Route
            path="deleteCar"
            element={<DeleteCar setCars={setCars} cars={cars} />}
          />
          <Route path="offers" element={<Offers />} />
          <Route path="users" element={<Users />} />
        </Route>
        {/*employee*/}
        <Route path="/emp/*" element={<EmpLayout />}>
          <Route index element={<ADMIN />} />
          <Route
            path="editCar"
            element={<EditCar setCars={setCars} cars={cars} />}
          />
          <Route
            path="deleteCar"
            element={<DeleteCar setCars={setCars} cars={cars} />}
          />
          <Route path="offers" element={<Offers />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
