import React from "react";
import { FaUserPlus, FaEdit, FaTrash, FaTags, FaTachometerAlt } from "react-icons/fa";

const ADMIN_NAV = () => {
  return (
    <div className="d-flex">
      
      {/* SIDEBAR */}
      <aside
        className="bg-light border-end"
        style={{
          width: "260px",
          height: "100vh",
          position: "fixed",
          top: 0,
          left: 0,
          paddingTop: "56px",
          overflowY: "auto",
          zIndex: 1030,
        }}
      >
        <ul className="nav flex-column px-3">

          <li className="nav-item mb-2">
            <a className="nav-link d-flex align-items-center gap-2 active" href="#">
              <FaTachometerAlt /> Dashboard
            </a>
          </li>

          <li className="nav-item mb-2">
            <a className="nav-link d-flex align-items-center gap-2" href="#">
              <FaUserPlus /> Add Employee
            </a>
          </li>

          <li className="nav-item mb-2">
            <a className="nav-link d-flex align-items-center gap-2" href="#">
              <FaEdit /> Edit car
            </a>
          </li>

          <li className="nav-item mb-2">
            <a className="nav-link d-flex align-items-center gap-2" href="#">
              <FaTrash /> Delete car
            </a>
          </li>

          <li className="nav-item mb-2">
            <a className="nav-link d-flex align-items-center gap-2" href="#">
              <FaTags /> Offers
            </a>
          </li>

        </ul>
      </aside>

      {/* MAIN AREA */}
      <div style={{ marginLeft: "260px", width: "100%" }}>
        {/* TOP NAVBAR */}
        <nav
          className="navbar navbar-dark bg-dark fixed-top shadow"
          style={{ height: "56px", zIndex: 1040 }}
        >
          <div className="container-fluid">
            <a className="navbar-brand text-white" href="#">
              Car Dealership
            </a>
          </div>
        </nav>

        {/* MAIN CONTENT */}
        <main className="p-4" style={{ marginTop: "70px" }}>
          {/* Leave empty or add dynamic content here */}
        </main>
      </div>
    </div>
  );
};

export default ADMIN_NAV;
