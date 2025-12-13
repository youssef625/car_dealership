
import { Link } from "react-router-dom";

const LoginNav = () => {
  return (
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
  );
};

export default LoginNav;
