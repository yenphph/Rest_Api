const distributesController = require('../Controller/distributor');
const router = require('express').Router();
//add
router.post("/", distributesController.addDistributors);
router.get("/", distributesController.getDistributors);
module.exports = router;