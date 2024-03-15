var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  //server xu ly du lieu va day data ve view
  res.render('index', { title: 'Express', title1: 'HTML' });
});

module.exports = router;
