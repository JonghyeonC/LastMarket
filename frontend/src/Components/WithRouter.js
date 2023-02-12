import { useLocation } from 'react-router-dom'; 

const withRouter = WrappedComponent => props => {
  const location = useLocation();
  // other hooks

  return (
    <WrappedComponent
      {...props}
      {...{ location }}
    />
  );
};

export default withRouter;