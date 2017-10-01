'use strict'
const http = require("http");
const fs = require('fs');
const path = require("path");

var testData = [];

for (var i = 2; i >= 0; i--) {

	var sumNum = Math.floor(Math.random()*2000);
	
	var colorNum = Math.floor(Math.random()*sumNum);
	
	testData.push(sumNum,colorNum, Math.random()>0.495,"\r");
};

testData = testData.toString().replace(/,\r,/g, ',\r');

fs.writeFile('./autotest.csv', testData, (err) => {
  if (err) throw err;
  console.log('New test file has been created!');
});