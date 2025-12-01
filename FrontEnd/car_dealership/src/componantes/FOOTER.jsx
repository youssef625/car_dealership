import React from 'react';

const FOOTER = () => {
  return (
    <footer
      style={{
        width: '100vw',
        marginLeft: 'calc(-50vw + 50%)',
        backgroundColor: '#f8f9fa', // light background
        color: '#212529',            // dark text
        padding: '3rem 0',
      }}
    >
      <div className="container" style={{ maxWidth: '1280px', margin: '0 auto' }}>
        {/* Navigation links */}
        <ul className="nav justify-content-center border-bottom pb-3 mb-3">
          <li className="nav-item">
            <a href="#" className="nav-link px-2 text-body-secondary">
              Home
            </a>
          </li>
          <li className="nav-item">
            <a href="#" className="nav-link px-2 text-body-secondary">
              Features
            </a>
          </li>
          <li className="nav-item">
            <a href="#" className="nav-link px-2 text-body-secondary">
              Pricing
            </a>
          </li>
          <li className="nav-item">
            <a href="#" className="nav-link px-2 text-body-secondary">
              FAQs
            </a>
          </li>
          <li className="nav-item">
            <a href="#" className="nav-link px-2 text-body-secondary">
              About
            </a>
          </li>
        </ul>

        {/* Footer text */}
        <p className="text-center mb-0">© 2025 Elite Cars Dealership</p>
      </div>
    </footer>
  );
};

export default FOOTER;
