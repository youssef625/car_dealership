import React from 'react';
import NAVBAR from '../componantes/NAVBAR.jsx';
import HERO from '../componantes/HERO.jsx';
import FEATURES from '../componantes/FEATURES.jsx';
import JUMB from '../componantes/JUMB.jsx';
import FOOTER from '../componantes/FOOTER.jsx';

const HOME = () => {
  return (
    <>
      {/* Navbar */}
      <NAVBAR />
      <div className="w-100 bg-light">

      {/* Hero Section */}
      <HERO />
</div>
      {/* Features Section */}
      <div className="w-100 bg-light">
        <FEATURES />
      </div>
      <JUMB />
      {/* Footer */}
      <FOOTER />
    </>
  );
};

export default HOME;
