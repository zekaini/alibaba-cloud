/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gdtopway.topway.common.xss.core;

/**
 * 利用 ThreadLocal 缓存线程间的数据
 *
 * @author L.cm
 */
class XssHolder {

	private static final ThreadLocal<Boolean> TL = new ThreadLocal<>();

	public static boolean isEnabled() {
		return Boolean.TRUE.equals(TL.get());
	}

	public static void setEnable() {
		TL.set(Boolean.TRUE);
	}

	public static void remove() {
		TL.remove();
	}

}
