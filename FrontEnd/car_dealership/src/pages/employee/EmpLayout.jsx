import {
  FaUserPlus,
  FaEdit,
  FaTrash,
  FaTags,
  FaTachometerAlt,
} from "react-icons/fa";
import { Link, Outlet } from "react-router-dom";

const EmpLayout = () => {
  return (
    <div className="d-flex w-100">
      {/* SIDEBAR */}
      <aside
        className="bg-light border-end d-none d-md-block"
        style={{
          width: "240px",
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
            <Link
              to="/emp/editCar"
              className="nav-link d-flex align-items-center gap-2"
            >
              <FaEdit /> Edit car
            </Link>
          </li>

          <li className="nav-item mb-2">
            <Link
              to="/emp/deleteCar"
              className="nav-link d-flex align-items-center gap-2"
            >
              <FaTrash /> Delete car
            </Link>
          </li>

          <li className="nav-item mb-2">
            <Link
              to="/emp/offers"
              className="nav-link d-flex align-items-center gap-2"
            >
              <FaTags /> Offers
            </Link>
          </li>
        </ul>
      </aside>

      {/* MAIN AREA */}
      <div
        className="flex-grow-1 admin-main"
        style={{
          marginLeft: "0px",
        }}
      >
        {/* TOP NAVBAR */}
        <nav
          className="navbar navbar-dark bg-dark fixed-top shadow"
          style={{ height: "56px", zIndex: 1040 }}
        >
          <div className="container-fluid">
            <button
              className="btn btn-outline-light d-md-none"
              data-bs-toggle="offcanvas"
              data-bs-target="#sidebarMenu"
            >
              ☰
            </button>

            <Link to="/" className="navbar-brand text-white" href="#">
              Car Dealership
            </Link>
          </div>
        </nav>

        {/* MAIN CONTENT */}
        <main className="w-100 " style={{ padding: "40px 20px" }}>
          <Outlet />
        </main>
      </div>

      {/* OFFCANVAS SIDEBAR IN SMALL SCREENS */}
      <div
        className="offcanvas offcanvas-start bg-light"
        tabIndex="-1"
        id="sidebarMenu"
      >
        <div className="offcanvas-header">
          <h5 className="offcanvas-title">Menu</h5>
          <button className="btn-close" data-bs-dismiss="offcanvas"></button>
        </div>

        <div className="offcanvas-body">
          <ul className="nav flex-column">
            

            <li className="nav-item mb-2">
              <Link
                to="/emp/editCar"
                className="nav-link d-flex align-items-center gap-2"
              >
                <FaEdit /> Edit car
              </Link>
            </li>

            <li className="nav-item mb-2">
              <Link
                to="/emp/deleteCar"
                className="nav-link d-flex align-items-center gap-2"
              >
                <FaTrash /> Delete car
              </Link>
            </li>

            <li className="nav-item mb-2">
              <Link
                to="/emp/offers"
                className="nav-link d-flex align-items-center gap-2"
              >
                <FaTags /> Offers
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default EmpLayout;
