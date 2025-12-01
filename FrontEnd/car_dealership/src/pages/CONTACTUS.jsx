import React from "react";
import NAVBAR from "../componantes/NAVBAR";

const CONTACTUS = () => {
  return (
    <>
      <NAVBAR />

      <section
        className="position-relative"
        style={{
          width: "100vw",
          marginLeft: "calc(-50vw + 50%)",
          backgroundColor: "#f8f9fa",
          padding: "5rem 0",
          minHeight: "100vh",
        }}
      >
        {/* Inner container to constrain content */}
        <div
          className="position-relative container"
          style={{
            maxWidth: "1280px",
            margin: "0 auto",
            zIndex: 2,
            display: "flex",
            flexWrap: "wrap",
            gap: "2rem",
          }}
        >
          {/* Contact Form */}
          <div className="flex-fill" style={{ minWidth: "300px", flex: "1 1 48%" }}>
            <div className="bg-white p-4 shadow rounded-3 h-100">
              <h3 className="fs-5 text-secondary mb-4">Contact</h3>
              <form action="#!">
                <div className="mb-3">
                  <label htmlFor="fullname" className="form-label fw-semibold">
                    Full Name <span className="text-danger">*</span>
                  </label>
                  <input
                    type="text"
                    className="form-control rounded-2 shadow-sm"
                    id="fullname"
                    name="fullname"
                    placeholder="Your Name"
                    required
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="email" className="form-label fw-semibold">
                    Email <span className="text-danger">*</span>
                  </label>
                  <input
                    type="email"
                    className="form-control rounded-2 shadow-sm"
                    id="email"
                    name="email"
                    placeholder="example@domain.com"
                    required
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="phone" className="form-label fw-semibold">
                    Phone Number
                  </label>
                  <input
                    type="tel"
                    className="form-control rounded-2 shadow-sm"
                    id="phone"
                    name="phone"
                    placeholder="+1 (555) 555-5555"
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="subject" className="form-label fw-semibold">
                    Subject <span className="text-danger">*</span>
                  </label>
                  <input
                    type="text"
                    className="form-control rounded-2 shadow-sm"
                    id="subject"
                    name="subject"
                    placeholder="Subject of your message"
                    required
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="message" className="form-label fw-semibold">
                    Message <span className="text-danger">*</span>
                  </label>
                  <textarea
                    className="form-control rounded-2 shadow-sm"
                    id="message"
                    name="message"
                    rows="4"
                    placeholder="Write your message here..."
                    required
                  ></textarea>
                </div>

                <div className="d-grid">
                  <button className="btn btn-primary btn-lg rounded-3" type="submit">
                    Send Message
                  </button>
                </div>
              </form>
            </div>
          </div>

          {/* Office Info */}
          <div className="flex-fill" style={{ minWidth: "300px", flex: "1 1 48%" }}>
            <div className="bg-white p-4 shadow rounded-3 h-100">
              <div className="mb-4">
                <h4 className="mb-2">Office</h4>
                <p className="text-secondary mb-2">Come visit us for a chat.</p>
                <hr className="mb-3 border-secondary" />
                <address className="text-secondary m-0">
                  8014 Edith Blvd NE, Albuquerque, New York, United States
                </address>
              </div>

              <div className="mb-4">
                <h4 className="mb-2">Phone</h4>
                <p className="text-secondary mb-2">Call us directly.</p>
                <hr className="mb-3 border-secondary" />
                <p className="m-0">
                  <a className="text-decoration-none text-primary" href="tel:+15057922430">
                    (505) 792-2430
                  </a>
                </p>
              </div>

              <div className="mb-4">
                <h4 className="mb-2">Email</h4>
                <p className="text-secondary mb-2">Write to us directly.</p>
                <hr className="mb-3 border-secondary" />
                <p className="m-0">
                  <a className="text-decoration-none text-primary" href="mailto:demo@yourdomain.com">
                    demo@yourdomain.com
                  </a>
                </p>
              </div>

              <div>
                <h4 className="mb-2">Opening Hours</h4>
                <p className="text-secondary mb-2">Business hours</p>
                <hr className="mb-3 border-secondary" />
                <div className="d-flex justify-content-between text-secondary mb-1">
                  <span className="fw-semibold">Mon - Fri</span>
                  <span>9am - 5pm</span>
                </div>
                <div className="d-flex justify-content-between text-secondary">
                  <span className="fw-semibold">Sat - Sun</span>
                  <span>9am - 2pm</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default CONTACTUS;
