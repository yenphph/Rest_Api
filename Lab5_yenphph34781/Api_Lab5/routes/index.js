var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});
// //
// router.get('/distributor-management', function (req, res, next) {
//   res.render('distributor-management', { title: 'distributor management' });
// });
module.exports = router;
