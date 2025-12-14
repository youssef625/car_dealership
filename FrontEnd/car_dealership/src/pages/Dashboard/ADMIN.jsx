import { FaUsers, FaCar, FaTags, FaTasks } from "react-icons/fa";

const ADMIN = () => {
  return (
    <div className="mt-5">
      <div className="container">
        <h2 className="fw-bold mb-4">Admin Dashboard</h2>

        <div className="row g-4">
          {/* Users Card */}
          <div className="col-md-3">
            <div className="card shadow-sm border-0 p-3">
              <div className="d-flex align-items-center gap-3">
                <FaUsers size={40} className="text-primary" />
                <div>
                  <h5 className="mb-1">Users</h5>
                  <p className="text-muted mb-0">Manage platform users</p>
                </div>
              </div>
            </div>
          </div>

          {/* Cars Card */}
          <div className="col-md-3">
            <div className="card shadow-sm border-0 p-3">
              <div className="d-flex align-items-center gap-3">
                <FaCar size={40} className="text-success" />
                <div>
                  <h5 className="mb-1">Cars</h5>
                  <p className="text-muted mb-0">Edit or delete car listings</p>
                </div>
              </div>
            </div>
          </div>

          {/* Offers Card */}
          <div className="col-md-3">
            <div className="card shadow-sm border-0 p-3">
              <div className="d-flex align-items-center gap-3">
                <FaTags size={40} className="text-warning" />
                <div>
                  <h5 className="mb-1">Offers</h5>
                  <p className="text-muted mb-0">Review and update offers</p>
                </div>
              </div>
            </div>
          </div>

          {/* Tasks Card */}
          <div className="col-md-3">
            <div className="card shadow-sm border-0 p-3">
              <div className="d-flex align-items-center gap-3">
                <FaTasks size={40} className="text-danger" />
                <div>
                  <h5 className="mb-1">Requests</h5>
                  <p className="text-muted mb-0">Pending employee tasks</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Section Below Cards */}
        <div className="mt-5">
          <div className="card shadow-sm border-0 p-4">
            <h4 className="fw-bold mb-3">System Overview</h4>
            <p className="text-muted">
              Welcome to the control panel. You can manage users, cars, offers,
              and employee assignments from the menu on the left. Explore the
              tools and keep your system up to date.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ADMIN;
