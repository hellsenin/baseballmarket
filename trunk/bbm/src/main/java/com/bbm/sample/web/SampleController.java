/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bbm.sample.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.property.EgovPropertyService;


/**  
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 * 
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 * 
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller

public class SampleController {
	
	   
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** Validator */
    /*
    @Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	*/
    
    
    @RequestMapping("/sample.do")
    public String selectSample() throws Exception {
        return "sample/index";
    }
    
    @RequestMapping("/layout.do")
    public String selectLayout() throws Exception {
        return "sample/layout";
    }
   
    
    /** 
     * validate 
     * @param  
     * @return 게시물 등록 JSP로 이동 ("sample/ccm/validator_help")
     * @exception Exception 
     * @see 
    */ 
    @RequestMapping(value="/sample/validator_help.do")
	public String validatorHelp() throws Exception {    
        return "sample/validate_help";
    }
    
    
}
