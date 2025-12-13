import React from 'react';

const JUMB = () => {
  return (
    <section
      className="position-relative"
      style={{
        width: '100vw',
        marginLeft: 'calc(-50vw + 50%)',
        backgroundColor: '#f8f9fa', // equivalent to bg-body-tertiary
        padding: '5rem 0',
      }}
    >
      {/* Inner container to constrain content */}
      <div
        className="position-relative container"
        style={{ maxWidth: '1280px', margin: '0 auto', zIndex: 2, textAlign: 'center' }}
      >
        <h1 className="text-body-emphasis mb-4">Drive Your Dream Car Today</h1>
        <p className="lead col-lg-8 mx-auto">
          Explore our wide range of premium vehicles, enjoy unbeatable deals, and experience exceptional service. At Elite Cars Dealership, your perfect car is just a test drive away.
        </p>
      </div>
    </section>
  );
};

export default JUMB;
