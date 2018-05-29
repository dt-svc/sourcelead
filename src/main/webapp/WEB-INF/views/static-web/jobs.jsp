<!--   HTML   -->	  
<jsp:include page="../staticHeader.jsp" />   
<div class="innerPageSearch">
		<section class="searchWrap">
			<span><i class="fa fa-search"></i><input type="text" placeholder="Search" class="searchHome"></span>
			<span><i class="fa fa-map-marker"></i><input type="text" placeholder="Location" class="searchHome"></span>
			<span><i class="fa fa-briefcase"></i>
				<select class="searchHome searchHomeE">
					<option>Exp (Years)</option>
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
					<option>5</option>
					<option>6</option>
				</select>
			</span>
			<span><input type="button" value="Search" class="searchBtn" onclick="window.location.href='job-results.html'"></span>
			<h3><b id="ADS">Advanced Search</b></h3>
			<div class="clear"></div>
		</section>
	</div>
	
<section class="innerContent">
		<div class="filter">
			<h2>Fliters</h2>
			<div class="filterCta">
				<h3>Posted Date<i class="fa fa-caret-down arrAc"></i></h3>
				<ul id="show">
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">Day ago
						  <input type="radio" name="ra"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">Day ago
						  <input type="radio" name="ra"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">Day ago
						  <input type="radio" name="ra"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">Day ago
						  <input type="radio" name="ra"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">Day ago
						  <input type="radio" name="ra"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<a href="javascript:void(0)">25 More</a>															
				</ul>
			</div>

			<div class="filterCta">
				<h3>Location<i class="fa fa-caret-down"></i></h3>
				<ul class="locaFil">
					<div class="FilInn">
					<h5>Country</h5>
					<li class="control-group">
						<label class="control control--checkbox">India
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">USA
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">UK
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>					
					</div>	
					<div class="FilInn">
					<h5>State</h5>
					<li class="control-group">
						<label class="control control--checkbox">Telangana
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Andhra Pradesh
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Delli
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>					
					</div>
					<div class="FilInn">
					<h5>City</h5>
					<li class="control-group">
						<label class="control control--checkbox">Hyderabad
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Secunderabad
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Warangal
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>					
					</div>
					<a href="javascript:void(0)">25 More</a>													
				</ul>
			</div>
			
			<div class="filterCta">
				<h3>Job Type<i class="fa fa-caret-down"></i></h3>
				<ul>
					<li class="control-group">
						<label class="control control--checkbox">Full time
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Part time
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">CTC
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">W2
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Intern
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<a href="javascript:void(0)">25 More</a>																	
				</ul>
			</div>
			
			<div class="filterCta">
				<h3>Company<i class="fa fa-caret-down"></i></h3>
				<ul>
					<li class="control-group">
						<label class="control control--checkbox">TCS
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">IBM
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">CTS
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Business Intelli
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">SaveLife
						  <input type="checkbox"/><div class="control__indicator"></div>
						</label>
					</li>
					<a href="javascript:void(0)">25 More</a>																	
				</ul>
			</div>																									
		</div>
		
<div class="filPop">
	<div class="filPopHead clearfix">
		<span>Filter name</span>
		<span><input type="text" placeholder="Search" class="filSearch"></span>
		<span>
			<select class="chosen-select" data-placeholder="Employers">
			  <option>Select Country</option>
			  <option>TCS</option>
			  <option>IBM</option>
			  <option>CTS</option>
			</select>			
		</span>
		<i class="fa fa-times filPopClose CP"></i>
	</div>
	<div class="filPopInn">
	
		<div class="LettSelec">
			<a>A</a><a>B</a><a>C</a><a>D</a><a>E</a><a>F</a><a>G</a><a>H</a><a>I</a><a>J</a><a>K</a><a>L</a><a>M</a>
			<a>N</a><a>O</a><a>P</a><a>Q</a><a>R</a><a>S</a><a>T</a><a>U</a><a>V</a><a>W</a><a>X</a><a>Y</a><a>Z</a>
			<a>Others</a>
		</div>
		
		<ul class="clearfix">
		<h3>State</h3>
			<li class="control-group">
				<label class="control control--checkbox">TCS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">IBM
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">CTS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">Business Intelli
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">SaveLife
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">TCS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">IBM
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">CTS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">Business Intelli
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">SaveLife
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">TCS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">IBM
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">CTS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">Business Intelli
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">SaveLife
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>																							
		</ul>
<ul class="clearfix">
		<h3>State</h3>
			<li class="control-group">
				<label class="control control--checkbox">TCS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">IBM
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">CTS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">Business Intelli
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">SaveLife
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">TCS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">IBM
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">CTS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">Business Intelli
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">SaveLife
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">TCS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">IBM
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">CTS
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">Business Intelli
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>
			<li class="control-group">
				<label class="control control--checkbox">SaveLife
				  <input type="checkbox"/><div class="control__indicator"></div>
				</label>
			</li>																							
		</ul>				
	</div>
	<div class="filPopBot clearfix">
		<div class="pull-right">
			<input type="button" class="btn btn-danger CP" value="Cancel"/>
			<input type="button" class="btn btn-primary" value="Clear All"/>
			<input type="button" class="btn btn-success" value="Search"/>
		</div>
	</div>
</div>		
		
		<div class="jobResults">
		
			<div class="fliterHead">
				<div>
					<span>Fliter 1 <i class="fa fa-remove"></i></span>
					<span>Fliter 2 <i class="fa fa-remove"></i></span>
					<span>Fliter 3 <i class="fa fa-remove"></i></span>
					<span>Fliter 4 <i class="fa fa-remove"></i></span>
					<span>Fliter 5 <i class="fa fa-remove"></i></span>
					<span>Fliter 6 <i class="fa fa-remove"></i></span>
					<span>Fliter 7 <i class="fa fa-remove"></i></span>
					<span>Fliter 8 <i class="fa fa-remove"></i></span>
				</div>
			</div>
			
			<div class="jobResultsHead">
				<p>Show results of 10 of 100</p>
				<span>
					<a class="act" href="javascript:void(0)">Relevance</a>
					<a href="javascript:void(0)">Freshness</a>
				</span>
				<div class="clear"></div>
				
			<div class="filLoad filLoad_a"><h2 class="gradi"></h2></div>
				
			</div>
			
			<div class="noResults"></div>	
			
			<div class="jobResultsCta">
				<div class="jobResultsInn" onclick="window.location.href='job-details.html'">
					<h3>Web developers</h3>
					<h4>Business Intelli</h4>
					<div class="skills">
						<strong>Skills :</strong>
						<span>Skills 1,</span>
						<span>Skills 2,</span>
						<span>Skills 3,</span>
						<span>Skills 4,</span>
						<span>Skills 5,</span>
						<span>Skills 6,</span>
					</div>					
					<span>
					  <b><i class="fa fa-clock-o"></i>Full time</b>
					  <b><i class="fa fa-briefcase"></i>3 to 5 Years</b>
					  <b><i class="fa fa-map-marker"></i>Hyderabad, India</b>
					</span>
				</div>
				<div class="jobShare">
					<div class="posted"><i class="fa fa-thumb-tack"></i>Posted : 6th Feb 2017</div>
					<div class="refer" title="Share this job"><i class="fa fa-bullhorn" aria-hidden="true"></i></div>
					<div class="share">
						<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>				  
					  	<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>					  	
				  	</div>
					<div class="clear"></div>
				</div>
				<div class="jobApply">
					<a href="http://localhost:8080/app/">Apply</a>
					<a class="Rcolor" href="javascript:void(0)">Refer</a>
				</div>
				
			<div class="filLoad">
				<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>
				<p class="gradi"></p>
			</div>				
			</div>
			
			<div class="jobResultsCta">
				<div class="jobResultsInn" onclick="window.location.href='job-details.html'">
					<h3>Web developer</h3>
					<h4>Business Intelli</h4>
					<div class="skills">
						<strong>Skills :</strong>
						<span>Skills 1,</span>
						<span>Skills 2,</span>
						<span>Skills 3,</span>
						<span>Skills 4,</span>
						<span>Skills 5,</span>
						<span>Skills 6,</span>
					</div>					
					<span>
					  <b><i class="fa fa-clock-o"></i>Full time</b>
					  <b><i class="fa fa-briefcase"></i>3 to 5 Years</b>
					  <b><i class="fa fa-map-marker"></i>Hyderabad, India</b>
					</span>
				</div>
				<div class="jobShare">
					<div class="posted"><i class="fa fa-thumb-tack"></i>Posted : 6th Feb 2017</div>
					<div class="refer" title="Share this job"><i class="fa fa-bullhorn" aria-hidden="true"></i></div>
					<div class="share">
						<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>				  
					  	<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>					  	
				  	</div>
					<div class="clear"></div>
				</div>
				<div class="jobApply"><a href="http://localhost:8080/app/">Apply</a></div>
				
			<div class="filLoad">
				<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>
				<p class="gradi"></p>
			</div>				
				
			</div>	
			
			<div class="jobResultsCta">
				<div class="jobResultsInn" onclick="window.location.href='job-details.html'">
					<h3>Web developer Web developer Web developer Web developer Web developer Web developer</h3>
					<h4>Business Intelli Business Intelli Business Intelli Business Intelli Business Intelli Business Intelli</h4>
					<div class="skills">
						<strong>Skills :</strong>
						<span>Skills 1,</span>
						<span>Skills 2,</span>
						<span>Skills 3,</span>
						<span>Skills 4,</span>
						<span>Skills 5,</span>
						<span>Skills 6,</span>
					</div>					
					<span>
					  <b><i class="fa fa-clock-o"></i>Full time</b>
					  <b><i class="fa fa-briefcase"></i>3 to 5 Years</b>
					  <b><i class="fa fa-map-marker"></i>Hyderabad, India</b>
					</span>
				</div>
				<div class="jobShare">
					<div class="posted"><i class="fa fa-thumb-tack"></i>Posted : 6th Feb 2017</div>
					<div class="refer" title="Share this job"><i class="fa fa-bullhorn" aria-hidden="true"></i></div>
					<div class="share">
						<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>				  
					  	<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>					  	
				  	</div>
					<div class="clear"></div>
				</div>
				<div class="jobApply">
					<a href="http://localhost:8080/app/">Apply</a>
					<a class="Rcolor" href="javascript:void(0)">Refer</a>
				</div>
				
			<div class="filLoad">
				<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>
				<p class="gradi"></p>
			</div>				
				
			</div>	
			
			<div class="jobResultsCta">
				<div class="jobResultsInn" onclick="window.location.href='job-details.html'">
					<h3>Web developer</h3>
					<h4>Business Intelli</h4>
					<div class="skills">
						<strong>Skills :</strong>
						<span>Skills 1,</span>
						<span>Skills 2,</span>
						<span>Skills 3,</span>
						<span>Skills 4,</span>
						<span>Skills 5,</span>
						<span>Skills 6,</span>
					</div>					
					<span>
					  <b><i class="fa fa-clock-o"></i>Full time</b>
					  <b><i class="fa fa-briefcase"></i>3 to 5 Years</b>
					  <b><i class="fa fa-map-marker"></i>Hyderabad, India</b>
					</span>
				</div>
				<div class="jobShare">
					<div class="posted"><i class="fa fa-thumb-tack"></i>Posted : 6th Feb 2017</div>
					<div class="refer" title="Share this job"><i class="fa fa-bullhorn" aria-hidden="true"></i></div>
					<div class="share">
						<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>				  
					  	<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>					  	
				  	</div>
					<div class="clear"></div>
				</div>
				<div class="jobApply"><a href="http://localhost:8080/app/">Apply</a></div>
				
			<div class="filLoad">
				<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>
				<p class="gradi"></p>
			</div>				
				
			</div>	

			<div class="jobResultsCta">
				<div class="jobResultsInn" onclick="window.location.href='job-details.html'">
					<h3>Web developer</h3>
					<h4>Business Intelli</h4>
					<div class="skills">
						<strong>Skills :</strong>
						<span>Skills 1,</span>
						<span>Skills 2,</span>
						<span>Skills 3,</span>
						<span>Skills 4,</span>
						<span>Skills 5,</span>
						<span>Skills 6</span>
					</div>					
					<span>
					  <b><i class="fa fa-clock-o"></i>Full time</b>
					  <b><i class="fa fa-briefcase"></i>3 to 5 Years</b>
					  <b><i class="fa fa-map-marker"></i>Hyderabad, India</b>
					</span>
				</div>
				<div class="jobShare">
					<div class="posted"><i class="fa fa-thumb-tack"></i>Posted : 6th Feb 2017</div>
					<div class="refer" title="Share this job"><i class="fa fa-bullhorn" aria-hidden="true"></i></div>
					<div class="share">
						<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>				  
					  	<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>
					  	<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>					  	
				  	</div>
					<div class="clear"></div>
				</div>
				<div class="jobApply"><a href="http://localhost:8080/app/">Apply</a></div>
				
			<div class="filLoad">
				<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>
				<p class="gradi"></p>
			</div>				
				
			</div>
		
<section class="loadJob"><i class="fa fa-spinner fa-spin"></i>View more</section>			
			
		</div>
		<div class="clear"></div>
	</section>	
<jsp:include page="../staticFooter.jsp" />