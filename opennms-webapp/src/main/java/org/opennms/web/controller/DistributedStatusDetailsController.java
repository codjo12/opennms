/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.web.controller;

import org.opennms.web.command.DistributedStatusDetailsCommand;
import org.opennms.web.svclayer.DistributedStatusService;
import org.opennms.web.svclayer.SimpleWebTable;
import org.opennms.web.validator.DistributedStatusDetailsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>DistributedStatusDetailsController class.</p>
 */
@Controller
@RequestMapping("/distributedStatusDetails.htm")
public class DistributedStatusDetailsController {

    @Autowired
    private DistributedStatusService m_distributedStatusService;

    @Autowired
    private DistributedStatusDetailsValidator m_validator;

    @RequestMapping(method={ RequestMethod.GET, RequestMethod.POST })
    public ModelAndView handle(@ModelAttribute("command") DistributedStatusDetailsCommand cmd, BindingResult errors) {
        m_validator.validate(cmd, errors);
        SimpleWebTable table = m_distributedStatusService.createStatusTable(cmd, errors);
        return new ModelAndView("distributedStatusDetails", "webTable", table);
    }
}
